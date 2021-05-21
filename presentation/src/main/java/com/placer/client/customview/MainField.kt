package com.placer.client.customview

import android.content.Context
import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.MainFieldListener
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard
import com.placer.client.util.extensions.FragmentExtensions.showKeyBoard
import com.placer.client.util.extensions.ViewExtensions.getFormatted
import com.placer.domain.entity.place.Place

internal class MainField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var editFieldView: EditText
    private lateinit var iconView: ImageView
    private lateinit var placesContainer: LinearLayout
    private lateinit var filterRadioGroup: RadioGroup
    private lateinit var filterButton: MaterialButton
    private var textWatcher: TextWatcher? = null
    private var fragment: Fragment? = null
    private var callback: MainFieldListener? = null
    private var filterGroups: List<Pair<Int, String>> =
        listOf(
            Pair(Constants.ALL_FILTER_GROUP, context.getString(R.string.map_places_filter_all_label)),
            Pair(Constants.MY_FILTER_GROUP, context.getString(R.string.map_places_filter_my_label))
        )

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?){
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_main_field, this)
        editFieldView = view.findViewById(R.id.editFieldView)
        iconView = view.findViewById(R.id.iconView)
        placesContainer = view.findViewById(R.id.placesContainer)
        filterRadioGroup = view.findViewById(R.id.filterRadioGroup)
        filterButton = view.findViewById(R.id.filterButton)
        setIconNormalState()
        setFilterClosedState()
        filterGroups.forEach {
            val radio = RadioButton(context)
            radio.id = it.first
            radio.text = it.second
            filterRadioGroup.addView(radio.getFormatted())
        }
        filterRadioGroup.check(Constants.ALL_FILTER_GROUP)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MainField)
        try {
            val hint = ta.getString(R.styleable.MainField_hint)
            hint?.let { editFieldView.hint = it }
        } finally {
            ta.recycle()
        }
    }

    private fun setIconNormalState() {
        iconView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_search_24)
        )
        iconView.setOnClickListener {
            editFieldView.requestFocus()
            fragment?.showKeyBoard()
        }
    }

    private fun setIconDeleteState(){
        iconView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24)
        )
        iconView.setOnClickListener {
            editFieldView.text.clear()
            editFieldView.clearFocus()
            fragment?.hideKeyBoard()
        }
    }

    internal fun setPlaces(places: List<PlaceView>){
        placesContainer.removeAllViews()
        places.forEach { place ->
           val view = PlaceItem(context)
           view.setPlace(place)
           view.getRoot().setOnClickListener {
               editFieldView.text.clear()
               editFieldView.clearFocus()
               callback?.placeSelected(place)
           }
           placesContainer.addView(view)
        }
    }

    fun setListener(callback: MainFieldListener, fragment: Fragment){
        editFieldView.removeTextChangedListener(textWatcher)
        textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                callback.textInMainFieldChanged(editFieldView.text.toString())
            }
        }
        editFieldView.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                setIconDeleteState()
                filterButton.visibility = View.GONE
                filterRadioGroup.visibility = View.GONE
            }else{
                setIconNormalState()
                setFilterClosedState()
            }
            callback.mainFieldFocusChanged(hasFocus)
        }
        editFieldView.addTextChangedListener(textWatcher)
        this.fragment = fragment
        this.callback = callback
    }

    private fun setFilterClosedState() {
        filterRadioGroup.visibility = View.GONE
        filterButton.visibility = View.VISIBLE
        filterButton.text = context.getString(R.string.map_places_filter_title)
        filterButton.setOnClickListener { setFilterOpenedState() }
    }

    private fun setFilterOpenedState() {
        filterRadioGroup.visibility = View.VISIBLE
        filterButton.text = context.getString(R.string.map_places_filter_close_label)
        filterButton.setOnClickListener { setFilterClosedState() }
        filterRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                Constants.ALL_FILTER_GROUP -> callback?.showAllPoints()
                Constants.MY_FILTER_GROUP -> callback?.showMyPoints()
            }
            setFilterClosedState()
        }
    }

    fun setHint(hint: String?){
        hint?.let {
            editFieldView.hint = it
        }
    }
}
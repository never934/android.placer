package com.placer.client.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.customview.places.PlaceItem
import com.placer.client.databinding.CustomviewMainFieldBinding
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.MainFieldListener
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard
import com.placer.client.util.extensions.FragmentExtensions.showKeyBoard
import com.placer.client.util.extensions.ViewExtensions.getFormatted

internal class MainField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewMainFieldBinding
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
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_main_field, this, true)
        setIconNormalState()
        setFilterClosedState()
        filterGroups.forEach {
            val radio = RadioButton(context)
            radio.id = it.first
            radio.text = it.second
            binding.filterRadioGroup.addView(radio.getFormatted())
        }
        binding.filterRadioGroup.check(Constants.ALL_FILTER_GROUP)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MainField)
        try {
            val hint = ta.getString(R.styleable.MainField_hint)
            hint?.let { binding.editFieldView.hint = it }
        } finally {
            ta.recycle()
        }
    }

    private fun setIconNormalState() {
        binding.iconView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_search_24)
        )
        binding.iconView.setOnClickListener {
            binding.editFieldView.requestFocus()
            fragment?.showKeyBoard()
        }
    }

    private fun setIconDeleteState(){
        binding.iconView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24)
        )
        binding.iconView.setOnClickListener {
            binding.editFieldView.text.clear()
            binding.editFieldView.clearFocus()
            fragment?.hideKeyBoard()
        }
    }

    internal fun setPlaces(places: List<PlaceView>){
        binding.placesContainer.removeAllViews()
        places.forEach { place ->
           val view = PlaceItem(context)
           view.setPlace(place)
           view.getRoot().setOnClickListener {
               binding.editFieldView.text.clear()
               binding.editFieldView.clearFocus()
               callback?.placeSelected(place)
           }
            binding.placesContainer.addView(view)
        }
    }

    fun setListener(callback: MainFieldListener, fragment: Fragment){
        binding.editFieldView.removeTextChangedListener(textWatcher)
        textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                callback.textInMainFieldChanged(binding.editFieldView.text.toString())
            }
        }
        binding.editFieldView.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                setIconDeleteState()
                binding.filterButton.visibility = View.GONE
                binding.filterRadioGroup.visibility = View.GONE
            }else{
                setIconNormalState()
                setFilterClosedState()
            }
            callback.mainFieldFocusChanged(hasFocus)
        }
        binding.editFieldView.addTextChangedListener(textWatcher)
        this.fragment = fragment
        this.callback = callback
    }

    private fun setFilterClosedState() {
        binding.filterRadioGroup.visibility = View.GONE
        binding.filterButton.visibility = View.VISIBLE
        binding.filterButton.text = context.getString(R.string.map_places_filter_title)
        binding.filterButton.setOnClickListener { setFilterOpenedState() }
    }

    private fun setFilterOpenedState() {
        binding.filterRadioGroup.visibility = View.VISIBLE
        binding.filterButton.text = context.getString(R.string.map_places_filter_close_label)
        binding.filterButton.setOnClickListener { setFilterClosedState() }
        binding.filterRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                Constants.ALL_FILTER_GROUP -> callback?.showAllPoints()
                Constants.MY_FILTER_GROUP -> callback?.showMyPoints()
            }
            setFilterClosedState()
        }
    }

    fun setHint(hint: String?){
        hint?.let {
            binding.editFieldView.hint = it
        }
    }
}
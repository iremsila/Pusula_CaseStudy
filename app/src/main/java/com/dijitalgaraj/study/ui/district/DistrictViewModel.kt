package com.dijitalgaraj.study.ui.district

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dijitalgaraj.study.base.BaseActionState
import com.dijitalgaraj.study.base.BaseViewModel
import com.dijitalgaraj.study.models.Place

class DistrictViewModel : BaseViewModel<BaseActionState>(
    DistrictActionState.Init
) {
    lateinit var baseDistrictList : List<Place>
    var selectedItemDistrict: Place?=null
    var place : Place? = null


}
package com.example.starwarscharacters.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Lorenzo Suarez on 5/5/2021.
 */

@Parcelize
open class ResponseHeader : Parcelable{
    val count: Int = 0
    val next: String = ""
    val previous: String = ""
}

package com.example.starwarscharacters.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

@Parcelize
open class Item(@Transient open val itemType: ItemType) : Parcelable

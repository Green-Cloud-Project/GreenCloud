/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class GreenCloudRestResponse<RespModel : Any>(
        @SerializedName("result")
        @ColumnInfo(name = "result")
        var result: Int,
        @SerializedName("errorCode")
        @ColumnInfo(name = "errorCode")
        var errorCode: Int,
        @SerializedName("errorMessage")
        @ColumnInfo(name = "errorMessage")
        var errorMessage: String,
        @SerializedName("model")
        @ColumnInfo(name = "model")
        var model: List<RespModel>
)

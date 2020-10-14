package com.sample.myapplication

import java.io.Serializable

data class BrowseData(
    var categoryName: String? = null,
    var contentName: String? = null,
    var contentDesc: String? = null,
    var contentVideoUrl: String? = null
)
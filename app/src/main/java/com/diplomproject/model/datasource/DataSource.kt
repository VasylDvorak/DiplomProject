package com.diplomproject.model.datasource

import com.diplomproject.model.data_word_request.DataModel
import com.diplomproject.model.data_description_request.DataModelId


interface DataSource<T> {

    suspend fun getData(word: String): List<DataModel>

    suspend fun getDataId(id: String): List<DataModelId>

}

package t.me.octopusapps.taskflow.data.local.datastore

interface DataStoreSource<T> {
    suspend fun saveValue(key: String, value: T)
    suspend fun getValue(key: String): T?
}
package t.me.octopusapps.taskflow.data.local.models

enum class FullPriority(val displayName: String) {
    A("A - Important & Urgent"),
    B("B - Important & Not urgent"),
    C("C - Not important & Urgent"),
    D("D - Not important & Not urgent")
}
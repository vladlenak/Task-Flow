package t.me.octopusapps.taskflow.domain.models

enum class FullPriority(val displayName: String) {
    A("A - Important & Urgent (do)"),
    B("B - Important & Not urgent (schedule)"),
    C("C - Not important & Urgent (delegate)"),
    D("D - Not important & Not urgent (delete)")
}
package it.giovanniandreuzza.liveservice

data class LiveResponse(val responseCommand: TEST, val responseValue: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LiveResponse

        if (responseCommand != other.responseCommand) return false
        if (!responseValue.contentEquals(other.responseValue)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = responseCommand.hashCode()
        result = 31 * result + responseValue.contentHashCode()
        return result
    }

}
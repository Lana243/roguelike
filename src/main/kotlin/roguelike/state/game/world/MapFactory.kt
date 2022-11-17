package roguelike.state.game.world

interface MapFactory {
    fun createMap(): String
}

class MapGenerator : MapFactory {

    override fun createMap() = rawMap

    // internal

    private val rawMap = """
        ################################################################################
        #                                                                              #
        #    P                                                                         #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        #                                                                              #
        ################################################################################
    """.trimIndent()
}
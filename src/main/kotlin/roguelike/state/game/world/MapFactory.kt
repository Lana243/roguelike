package roguelike.state.game.world

interface MapFactory {
    fun createMap(): String
}

class MapConstant : MapFactory {

    override fun createMap() = rawMap

    // internal

    private val rawMap = """
        ################################################################################
        #    #                                                                         #
        # P         W  S                                                               #
        #    #                                                                         #
        #    #                                                                         #
        # A  #                                                                         #
        #    ####                                                                      #
        #  A                                                                           #
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
        #                                                                            D #
        #                                                                              #
        ################################################################################
    """.trimIndent()
}


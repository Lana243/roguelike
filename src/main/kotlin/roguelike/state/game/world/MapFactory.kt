package roguelike.state.game.world

/**
 * Интерфейс для генерации карты
 */
interface MapFactory {
    fun createMap(): String
}

/**
 * Константная карта, задаваемая в игре
 */
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


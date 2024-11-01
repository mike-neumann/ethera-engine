package me.etheraengine.banner

import org.springframework.boot.Banner
import org.springframework.core.env.Environment
import java.io.PrintStream

class EtheraBanner : Banner {
    override fun printBanner(environment: Environment, sourceClass: Class<*>, out: PrintStream) {
        out.print(
            """
  .
 /\\   _          _                  ______
( ( ) / \   _ __ (_)_ __ ___   __ _  \ \ \ \ 
 \\/ / _ \ | '_ \| | '_ ` _ \ / _` |  \ \ \ \
  , / ___ \| | | | | | | | | | (_| |  / / / /
===/_/===\_\_| |_|_|_| |_| |_|\__,_|=/_/_/_/ 
        """
        )
    }
}

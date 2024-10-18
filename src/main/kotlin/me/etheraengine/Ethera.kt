package me.etheraengine

import me.etheraengine.banner.EtheraBanner
import me.etheraengine.config.EtheraConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

@Order(Ordered.HIGHEST_PRECEDENCE)
@SpringBootApplication(scanBasePackages = ["me.etheraengine"])
open class Ethera(
    private val etheraConfig: EtheraConfig,
    private val window: Window
) : CommandLineRunner {
    companion object {
        private lateinit var windowTitle: String
        lateinit var context: ConfigurableApplicationContext
            private set
        lateinit var frame: JFrame

        /**
         * Initializes the Ethera using Spring Boot
         */
        fun run(game: Class<*>, title: String) {
            SwingUtilities.invokeLater {
                // wait until AWT and Swing is ready
            }

            windowTitle = title
            context =
                SpringApplicationBuilder().sources(Ethera::class.java, game)
                    .banner(EtheraBanner())
                    .run()
        }
    }

    override fun run(vararg args: String) {
        frame = JFrame(windowTitle)
        frame.add(window)
        frame.size = Dimension(etheraConfig.width, etheraConfig.height)
        frame.isVisible = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.addFocusListener(window)
        frame.addKeyListener(window)
        frame.addMouseMotionListener(window)
        frame.addMouseListener(window)
        frame.addMouseWheelListener(window)
    }
}

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)
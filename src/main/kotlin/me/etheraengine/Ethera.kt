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
import java.awt.Font
import java.io.File
import javax.swing.JFrame
import javax.swing.SwingUtilities

@Order(Ordered.HIGHEST_PRECEDENCE)
@SpringBootApplication(scanBasePackages = ["me.etheraengine"])
open class Ethera(
    val etheraConfig: EtheraConfig,
    val screen: Screen,
) : CommandLineRunner {
    companion object {
        lateinit var windowTitle: String
            private set

        lateinit var context: ConfigurableApplicationContext
            private set

        lateinit var frame: JFrame
            private set

        @PublishedApi
        internal fun <T> run(type: Class<T>, title: String): T {
            SwingUtilities.invokeLater {
                // wait until AWT and Swing is ready
            }

            windowTitle = title
            context =
                SpringApplicationBuilder().sources(Ethera::class.java, type)
                    .banner(EtheraBanner())
                    .run()

            return context.getBean(type)
        }

        /**
         * Initializes Ethera using Spring Boot
         */
        inline fun <reified T : Any> run(title: String) = run(T::class.java, title)
    }

    override fun run(vararg args: String) {
        frame = JFrame(windowTitle)
        frame.add(screen)
        frame.size = Dimension(etheraConfig.width, etheraConfig.height)
        frame.isVisible = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.addFocusListener(screen)
        frame.addKeyListener(screen)
        frame.addMouseMotionListener(screen)
        frame.addMouseListener(screen)
        frame.addMouseWheelListener(screen)
        // we want to manually handle ui, so we have to disable focus traversal
        frame.focusTraversalKeysEnabled = false

        // set default font if specified in config
        if (etheraConfig.fontUrl != "") {
            screen.font = Font.createFont(Font.TRUETYPE_FONT, File(etheraConfig.fontUrl).toURI().toURL().openStream())
        }
    }
}

/**
 * Utility function to retrieve a typed logger instance
 */
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)
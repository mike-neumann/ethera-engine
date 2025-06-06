package me.etheraengine.runtime

import me.etheraengine.runtime.service.ConfigurationService
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
open class Ethera(val configurationService: ConfigurationService, val screen: Screen) : CommandLineRunner {
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
            context = SpringApplicationBuilder().sources(Ethera::class.java, type).run()
            return context.getBean(type)
        }

        /**
         * Initializes Ethera using Spring Boot
         */
        inline fun <reified T : Any> run(title: String) = run(T::class.java, title)
    }

    override fun run(vararg args: String) {
        frame = JFrame(windowTitle).apply {
            add(screen)
            size = Dimension(configurationService.width, configurationService.height)
            isVisible = true
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            addFocusListener(screen)
            addKeyListener(screen)
            addMouseMotionListener(screen)
            addMouseListener(screen)
            addMouseWheelListener(screen)
            // we want to manually handle ui, so we have to disable focus traversal
            focusTraversalKeysEnabled = false
        }
        // set default font if specified in config
        if (configurationService.fontUrl != "") {
            screen.font = Font.createFont(Font.TRUETYPE_FONT, File(configurationService.fontUrl).toURI().toURL().openStream())
        }
    }
}

/**
 * Utility function to retrieve a typed logger instance
 */
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)
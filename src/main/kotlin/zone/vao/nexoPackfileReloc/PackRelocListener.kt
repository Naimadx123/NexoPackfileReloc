package zone.vao.nexoPackfileReloc

import com.nexomc.nexo.api.events.resourcepack.NexoPostPackGenerateEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PackRelocListener(private val plugin: NexoPackfileReloc) : Listener {

    @EventHandler
    fun onPackGenerate(event: NexoPostPackGenerateEvent) {

        val copyTo = plugin.config.getString("copy_to_path", "/generated/pack.zip")!!

        runCatching { plugin.copyPackZip(copyTo) }
            .onSuccess {
                plugin.logger.info("Pack file copied -> $copyTo")
            }
            .onFailure {
                plugin.logger.warning("Pack file copy failed : ${it.message}")
            }
    }
}

package me.nepnep.zerodupe

import com.lambda.client.command.ClientCommand
import com.lambda.client.event.SafeExecuteEvent
import com.lambda.client.util.items.itemPayload
import com.lambda.client.util.text.MessageSendHelper
import net.minecraft.item.ItemWritableBook
import net.minecraft.nbt.NBTTagList
import net.minecraft.nbt.NBTTagString

object DupeCommand : ClientCommand(
    name = "0bdupe",
    description = "Makes a book for duping on 0b0t"
) {
    init {
        executeSafe {
            createBook()
        }
    }

    // The packet part of this code comes from bookbot
    private fun SafeExecuteEvent.createBook() {
        val heldStack = player.inventory.getCurrentItem()

        if (heldStack.item is ItemWritableBook) {
            val content = NBTTagString(buildString {
                for (i in 1..170) { // 168 probably works
                    append('a')
                }
            })

            val pages = NBTTagList()
            for (page in 1..10) {
                pages.appendTag(content)
            }

            if (heldStack.hasTagCompound()) {
                heldStack.tagCompound!!.setTag("pages", pages)
                heldStack.tagCompound!!.setTag("title", NBTTagString(""))
                heldStack.tagCompound!!.setTag("author", NBTTagString(player.name))
            } else {
                heldStack.setTagInfo("pages", pages)
                heldStack.setTagInfo("title", NBTTagString(""))
                heldStack.setTagInfo("author", NBTTagString(player.name))
            }

            itemPayload(heldStack, "MC|BEdit")

            MessageSendHelper.sendChatMessage("Dupe book generated.")
        } else {
            MessageSendHelper.sendErrorMessage("You must be holding a writable book.")
        }
    }
}
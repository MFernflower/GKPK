package HxCKDMS.gkpk.block.containers;

import HxCKDMS.gkpk.block.tile.TileEntityFermenter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;


public class ContainerFermenter extends Container {
    public TileEntityFermenter tile;

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    public ContainerFermenter(EntityPlayer ply, TileEntity tile) {
        // Container slots
        addSlotToContainer(new Slot((IInventory)tile,0,39,35));
        addSlotToContainer(new Slot((IInventory)tile,1,63,35));
        addSlotToContainer(new Slot((IInventory)tile,2,118,35));
        // END

        this.tile = (TileEntityFermenter) tile;
        bindPlayerInventory(ply.inventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < inventorySlots.size()) {
                if (!this.mergeItemStack(stackInSlot, tile.getSizeInventory(), 36 + tile.getSizeInventory(), true)) {
                    return null;
                }
            }
            //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, tile.getSizeInventory(), false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
}

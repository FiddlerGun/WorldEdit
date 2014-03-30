/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.function;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.masks.ExistingBlockMask;
import com.sk89q.worldedit.masks.Mask;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Applies a {@link RegionFunction} to the first ground block.
 */
public class GroundFunction implements LayerFunction {

    private final EditSession editSession;
    private final RegionFunction function;
    private Mask mask = new ExistingBlockMask();
    private int affected;

    /**
     * Create a new ground function.
     *
     * @param editSession an edit session
     * @param function the function to apply
     */
    public GroundFunction(EditSession editSession, RegionFunction function) {
        checkNotNull(editSession);
        checkNotNull(function);
        this.editSession = editSession;
        this.function = function;
    }

    /**
     * Get the mask that determines what the ground consists of.
     *
     * @return a mask
     */
    public Mask getMask() {
        return mask;
    }

    /**
     * Set the mask that determines what the ground consists of.
     *
     * @param mask a mask
     */
    public void setMask(Mask mask) {
        checkNotNull(mask);
        this.mask = mask;
    }

    /**
     * Get the number of affected objects.
     *
     * @return the number of affected
     */
    public int getAffected() {
        return affected;
    }

    @Override
    public boolean isGround(Vector position) {
        return mask.matches(editSession, position);
    }

    @Override
    public boolean apply(Vector position, int depth) throws WorldEditException {
        if (depth == 0) {
            if (function.apply(position)) {
                affected++;
            }
        }

        return false;
    }

}

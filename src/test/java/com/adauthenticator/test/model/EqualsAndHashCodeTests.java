/*
 * Copyright (C) 2015  Keith Lyall (https://github.com/klyall/ad-authenticator)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.adauthenticator.test.model;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public abstract class EqualsAndHashCodeTests<T> {

    protected abstract T getReferenceObject1();
    protected abstract T getReferenceObject2();
    protected abstract T getReferenceObject3();
    protected abstract Map<String, T> getDifferentObjects();

    @Test
    public void equalsShouldBeReflexive() {
        assertEquals("Reflexive: Same object should equal itself", getReferenceObject1(), getReferenceObject1());
    }

    @Test
    public void equalsShouldBeSymmetric() {
        assertEquals("Symmetric: Object A should equal object B", getReferenceObject1(), getReferenceObject2());
        assertEquals("Symmetric: Object B should equal object A", getReferenceObject2(), getReferenceObject1());
    }

    @Test
    public void equalsShouldBeTransitive() {
        assertEquals("Symmetric: Object A should equal object B", getReferenceObject1(), getReferenceObject2());
        assertEquals("Symmetric: Object B should equal object C", getReferenceObject2(), getReferenceObject3());
        assertEquals("Symmetric: Object C should equal object A", getReferenceObject3(), getReferenceObject1());
    }

    @Test
    public void equalsShouldBeFalseWhenNull() {
        assertNotEquals("Symmetric: Object A should not equal null", getReferenceObject1(), null);
    }

    @Test
    public void equalsShouldBeFalseWithDifferentAttributeValues() {
        for (String key: getDifferentObjects().keySet()) {
            assertNotEquals(String.format("Objects with different priority are equal", key),
                    getReferenceObject1(),
                    getDifferentObjects().get(key));
        }
    }

    @Test
    public void hashCodeShouldBeSameForSameObjects() {
        assertEquals("Same object has different hashCode", getReferenceObject1().hashCode(), getReferenceObject1().hashCode());
    }

    @Test
    public void hashCodeShouldBeSameForEqualObjects() {
        assertEquals("Same object has different hashCode", getReferenceObject1().hashCode(), getReferenceObject2().hashCode());
    }

    @Test
    public void hashCodeShouldBeDifferentWithDifferentAttributeValues() {
        for (String key: getDifferentObjects().keySet()) {
            assertNotEquals(String.format("Objects with different priority are equal", key),
                    getReferenceObject1().hashCode(),
                    getDifferentObjects().get(key).hashCode());
        }
    }
}

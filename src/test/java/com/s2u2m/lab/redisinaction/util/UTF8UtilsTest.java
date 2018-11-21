/*
 *     Copyright (C) <2018>  <s2u2m>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.s2u2m.lab.redisinaction.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
 * UTF8UtilsTest create on 2018/11/20
 *
 * @author Amos Xia
 */
public class UTF8UtilsTest {

    @Test
    public void predecessor() throws UnsupportedEncodingException {
        String input = "b";
        String expect = "a" + UTF8Utils.convertInt2UTF8Char(UTF8Utils.UTF8_MAX);
        String predecessor = UTF8Utils.predecessor(input);
        assertEquals(expect, predecessor);
    }

    @Test
    public void successor() throws UnsupportedEncodingException {
        String input = "a";
        String expect = input + UTF8Utils.convertInt2UTF8Char(UTF8Utils.UTF8_MAX);
        String successor = UTF8Utils.successor(input);
        assertEquals(expect, successor);
    }
}
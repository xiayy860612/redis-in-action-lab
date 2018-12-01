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

package com.s2u2m.lab.redisinaction.controller.lab060102;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * UTF8Utils create on 2018/11/19
 *
 * @author Amos Xia
 */
public final class UTF8Utils {

    public static final String UTF8 = "UTF-8";
    /**
     * {@literal https://zh.wikipedia.org/wiki/UTF-8}
     */
    public static final int UTF8_MIN = 0x00000000;
    public static final int UTF8_MAX = 0xF7BFBFBF;

    public static String keyConvert(String input)
            throws UnsupportedEncodingException {
        return new String(input.getBytes(UTF8), UTF8);
    }

    public static String predecessor(String input)
            throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder(
                input.substring(0, input.length() - 1));
        String tailChar = input.substring(input.length() - 1);
        int tail = convertUTF8Char2Int(tailChar);
        int suffix = tail != UTF8_MIN ? tail - 1 : UTF8_MIN;
        String suffixChar = convertInt2UTF8Char(suffix);
        String endChar = convertInt2UTF8Char(UTF8_MAX);
        return builder
                .append(suffixChar)
                .append(endChar).toString();
    }

    public static String successor(String input)
            throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder(input);
        String endChar = convertInt2UTF8Char(UTF8_MAX);
        return builder.append(endChar).toString();
    }

    public static int convertUTF8Char2Int(String lastChar)
            throws UnsupportedEncodingException {
        byte[] bs = lastChar.getBytes(UTF8);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        System.arraycopy(bs, 0,
                buffer.array(), buffer.capacity() - bs.length, bs.length);
        return buffer.getInt();
    }

    public static String convertInt2UTF8Char(int value)
            throws UnsupportedEncodingException {
        byte[] bs = ByteBuffer.allocate(Integer.BYTES)
                .putInt(value).array();
        int index = 0;
        while(index < bs.length) {
            byte b = bs[index];
            if (b == 0) {
                ++index;
                continue;
            }
            break;
        }

        int resultLen = bs.length - index;
        ByteBuffer result = ByteBuffer.allocate(resultLen);
        System.arraycopy(bs, index, result.array(), 0, resultLen);
        return new String(result.array(), UTF8);
    }
}

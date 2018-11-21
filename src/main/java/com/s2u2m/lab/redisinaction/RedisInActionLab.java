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

package com.s2u2m.lab.redisinaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * RedisInActionLab create on 2018/11/19
 *
 * @author Amos Xia
 */
@SpringBootApplication
public class RedisInActionLab extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RedisInActionLab.class, args);
    }
}

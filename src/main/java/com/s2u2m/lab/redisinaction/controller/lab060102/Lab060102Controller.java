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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lab060102Controller create on 2018/11/19
 *
 * @author Amos Xia
 */
@RestController
@RequestMapping("/lab/06/01/02")
public class Lab060102Controller {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String AUTO_FILL_AUTHOR_NAME = "auto_fill:author";

    @PutMapping("/auto-fill/author/{name}")
    public ResponseEntity<String> inputAuthor(@PathVariable String name)
            throws UnsupportedEncodingException {
        String key = UTF8Utils.keyConvert(name);
        Boolean rst = redisTemplate.opsForZSet().add(AUTO_FILL_AUTHOR_NAME, key, 0);
        return ResponseEntity.ok(key);
    }

    @GetMapping("/auto-fill/author")
    public ResponseEntity<List<String>> getAutoFillList(
            @RequestParam("prefix") String prefix,
            @RequestParam(value = "count", defaultValue = "0") int count) throws UnsupportedEncodingException {
        String start = UTF8Utils.predecessor(prefix);
        String end = UTF8Utils.successor(prefix);

        Set<ZSetOperations.TypedTuple<String>> ranges = new HashSet<>();
        ranges.add(new DefaultTypedTuple<>(start, 0.0));
        ranges.add(new DefaultTypedTuple<>(end, 0.0));
        redisTemplate.opsForZSet().add(AUTO_FILL_AUTHOR_NAME, ranges);

        long si = redisTemplate.opsForZSet().rank(AUTO_FILL_AUTHOR_NAME, start);
        long ei = redisTemplate.opsForZSet().rank(AUTO_FILL_AUTHOR_NAME, end);
        Set<String> items = redisTemplate.opsForZSet().range(AUTO_FILL_AUTHOR_NAME, si+1, ei-1);
        redisTemplate.opsForZSet().remove(AUTO_FILL_AUTHOR_NAME, start, end);
        return ResponseEntity.ok(new ArrayList<>(items));
    }




}

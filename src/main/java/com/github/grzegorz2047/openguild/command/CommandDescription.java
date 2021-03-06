/*
 * The MIT License
 *
 * Copyright 2014 Aleksander.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.grzegorz2047.openguild.command;

import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandDescription {

    private final HashMap<String, String> list = new HashMap<String, String>();
    private String desc;

    public String get(@Nonnull String lang) {
        if(desc != null)
            return desc;
        else
            return list.get(lang);
    }

    public boolean has(@Nonnull String lang) {
        if(desc != null)
            return true;
        else
            return list.containsKey(lang.toUpperCase());
    }

    public void set(@Nullable String description) {
        desc = description;
    }

    public void set(@Nonnull String lang, @Nullable String description) {
        list.remove(lang.toUpperCase());
        list.put(lang.toUpperCase(), description);
    }

}

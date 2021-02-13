package com.physmo.romexplorer.tilers;

import java.util.ArrayList;
import java.util.List;

public class TilerRepo {
    List<Tiler> tilers = new ArrayList<>();

    public TilerRepo() {
        tilers.add(new Tiler16Col());
        tilers.add(new Tiler256Col());
        tilers.add(new TilerGB());
        tilers.add(new TilerMono8By8());
        tilers.add(new TilerSega4Col());
        tilers.add(new TilerSega16Col());
        tilers.add(new TilerSnes4BPP());
    }

    public String[] getNameList() {
        String[] list = new String[tilers.size()];
        int pos = 0;
        for (Tiler tiler : tilers) {
            list[pos++] = tiler.getName();
        }
        return list;
    }

    public Tiler getByName(String name) {
        for (Tiler tiler : tilers) {
            if (tiler.getName().equals(name)) return tiler;
        }
        return null;
    }
}

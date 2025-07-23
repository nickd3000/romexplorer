package com.physmo.romexplorer.tilers;

import java.util.ArrayList;
import java.util.List;

public class TilerRepo {
    List<Tiler> tilers = new ArrayList<>();

    public TilerRepo() {
        tilers.add(new Tiler16Col(1,1,false));
        tilers.add(new Tiler16Col(8,8,false));
        tilers.add(new Tiler16Col(16,16,false));
        tilers.add(new Tiler16Col(32,32, false));
        tilers.add(new Tiler16Col(8,8,true));
        tilers.add(new Tiler16Col(16,16,true));
        tilers.add(new Tiler256Col(1,1));
        tilers.add(new Tiler256Col(8,8));
        tilers.add(new Tiler256Col(16,16));
        tilers.add(new Tiler256Col(32,32));
        tilers.add(new Tiler256Col(64,64));
        tilers.add(new TilerGB());
        tilers.add(new TilerNES());
        tilers.add(new TilerMono8By8());
        tilers.add(new TilerDuo8By8());
        tilers.add(new TilerSega4Col());
        tilers.add(new TilerSega16Col());
        tilers.add(new TilerSnes4BPP());
        tilers.add(new TilerAmiga16p4Col());
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

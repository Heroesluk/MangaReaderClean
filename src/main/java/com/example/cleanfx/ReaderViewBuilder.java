package com.example.cleanfx;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class ReaderViewBuilder implements Builder<Region>{

    @Override
    public Region build(){
        BorderPane results = new BorderPane();
        results.setTop(new Label("Customer Information"));
        return results;
    }
}

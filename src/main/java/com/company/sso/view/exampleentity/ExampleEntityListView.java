package com.company.sso.view.exampleentity;

import com.company.sso.entity.ExampleEntity;

import com.company.sso.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "example", layout = MainView.class)
@ViewController("ExampleEntity.list")
@ViewDescriptor("example-entity-list-view.xml")
@LookupComponent("exampleEntitiesDataGrid")
@DialogMode(width = "64em")
public class ExampleEntityListView extends StandardListView<ExampleEntity> {
}
package com.company.sso.view.exampleentity;

import com.company.sso.entity.ExampleEntity;

import com.company.sso.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "exampleEntities/:id", layout = MainView.class)
@ViewController("ExampleEntity.detail")
@ViewDescriptor("example-entity-detail-view.xml")
@EditedEntityContainer("exampleEntityDc")
public class ExampleEntityDetailView extends StandardDetailView<ExampleEntity> {
}
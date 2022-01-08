package com.isd.ict.capstoneproject.views.handler;
/**
 * The {@link ScreenElementObserver screenElementObserver}
 * interface provides functionality for handler.
 * @author Group 3
 */
public interface ScreenElementObserver {
    /**
     * Update screen
     * @param observedObject
     * @throws Exception
     */
    void update(ScreenElementObservable observedObject) throws Exception;
}

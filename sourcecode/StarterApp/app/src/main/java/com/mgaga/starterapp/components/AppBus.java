package com.mgaga.starterapp.components;

import org.greenrobot.eventbus.EventBus;

public class AppBus extends EventBus {

    public void onDetailedLogFilter(String fromDate, String toDate) {
        post(new AppBusEvent(AppBusEventType.DETAILED_LOG_FILTER, fromDate, toDate));
    }

    public void onLogOut() {
        post(new AppBusEvent(AppBusEventType.ON_LOG_OUT));
    }

    public class AppBusEvent {
        private final AppBusEventType event;
        private final Object data1;
        private final Object data2;

        public AppBusEvent(AppBusEventType event) {
            this(event, null, null);
        }

        public AppBusEvent(AppBusEventType event, Object data) {
            this(event, data, null);
        }

        public AppBusEvent(AppBusEventType eventIn, Object data1In, Object data2In) {
            event = eventIn;
            data1 = data1In;
            data2 = data2In;
        }

        public AppBusEventType getType() {
            return event;
        }

        public Object getData1() {
            return data1;
        }

        public Object getData2() {
            return data2;
        }
    }

    public enum AppBusEventType {
        DETAILED_LOG_FILTER,
        ON_LOG_OUT,
    }
}

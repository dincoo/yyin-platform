package com.yy.platform.system.management.contants;

public class Constant {
    /** 超级管理员ID */
    public static final String SUPER_ADMIN = "1";
    /** 数据权限过滤 */
    public static final String SQL_FILTER = "sql_filter";


    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 模块
         */
        MODULES(0),
        /**
         * 目录
         */
        CATALOG(1),
        /**
         * 菜单
         */
        MENU(2),
        /**
         * 按钮
         */
        BUTTON(3);

        /*
         * MODULES
         */
        //MODULES(3);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
package com.code.filesystem.util;

/**
 * Created by MaJian on 18/5/5.
 */
public class Enum {

    /**
     * 返回值类型
     */
    public enum CodeType {

        /**
         * 成功
         */
        Success(0),

        /**
         * 文件不存在
         */
        NoFile(1),

        /**
         * 错误
         */
        Error(-1);

        public int seq;
        CodeType(int seq){
            this.seq = seq;
        }
    }
}

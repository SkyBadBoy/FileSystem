package com.code.filesystem.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MaJian on 18/5/5.
 */
@Getter
@Setter
public class File {
    private String Url;
    private String Path;
    private long Size;
    private boolean OSS;
}

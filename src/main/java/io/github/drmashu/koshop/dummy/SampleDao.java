package io.github.drmashu.koshop.dummy;

import org.seasar.doma.*;

@Dao
public interface SampleDao {
    @Select
    String select();
}

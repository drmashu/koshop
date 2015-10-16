package io.github.drmashu.koshop.doma;

/**
 * Created by drmashu on 2015/10/16.
 */

import org.seasar.doma.AnnotateWith;

import io.github.drmashu.dikon.inject;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;

/**
 * Created by drmashu on 2015/10/16.
 */
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CONSTRUCTOR_PARAMETER, type = inject.class, elements = "name=\"doma_config\"")
})
public @interface InjectDomaConfig {
}

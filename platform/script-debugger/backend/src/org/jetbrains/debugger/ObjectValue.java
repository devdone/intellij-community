package org.jetbrains.debugger;

import com.intellij.openapi.util.AsyncResult;
import com.intellij.util.ThreeState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A compound value that has zero or more properties
 */
public interface ObjectValue extends Value {
  @Nullable
  String getClassName();

  @NotNull
  AsyncResult<List<? extends Variable>> getProperties();

  /**
   * It must return quickly. Return {@link com.intellij.util.ThreeState#UNSURE} otherwise.
   */
  @NotNull
  ThreeState hasProperties();

  int getCacheState();
}
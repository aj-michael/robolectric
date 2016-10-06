package org.robolectric.internal.bytecode;

import org.robolectric.annotation.Implements;

public class ShadowConfig {
  public final String shadowClassName;
  public final boolean callThroughByDefault;
  public final boolean inheritImplementationMethods;
  public final boolean looseSignatures;
  private final int fromSdk;
  private final int toSdk;

  ShadowConfig(String shadowClassName, boolean callThroughByDefault, boolean inheritImplementationMethods,
               boolean looseSignatures, int fromSdk, int toSdk) {
    this.shadowClassName = shadowClassName;
    this.callThroughByDefault = callThroughByDefault;
    this.inheritImplementationMethods = inheritImplementationMethods;
    this.looseSignatures = looseSignatures;
    this.fromSdk = fromSdk;
    this.toSdk = toSdk;
  }

  ShadowConfig(String shadowClassName, Implements annotation) {
    this(shadowClassName,
        annotation.callThroughByDefault(),
        annotation.inheritImplementationMethods(),
        annotation.looseSignatures(),
        annotation.from(),
        annotation.to());
  }

  public boolean supportsSdk(int sdkInt) {
    return fromSdk <= sdkInt && (toSdk == -1 || toSdk >= sdkInt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ShadowConfig that = (ShadowConfig) o;

    if (callThroughByDefault != that.callThroughByDefault) return false;
    if (inheritImplementationMethods != that.inheritImplementationMethods) return false;
    if (looseSignatures != that.looseSignatures) return false;
    if (fromSdk != that.fromSdk) return false;
    if (toSdk != that.toSdk) return false;
    return shadowClassName != null ? shadowClassName.equals(that.shadowClassName) : that.shadowClassName == null;

  }

  @Override
  public int hashCode() {
    int result = shadowClassName != null ? shadowClassName.hashCode() : 0;
    result = 31 * result + (callThroughByDefault ? 1 : 0);
    result = 31 * result + (inheritImplementationMethods ? 1 : 0);
    result = 31 * result + (looseSignatures ? 1 : 0);
    result = 31 * result + fromSdk;
    result = 31 * result + toSdk;
    return result;
  }
}

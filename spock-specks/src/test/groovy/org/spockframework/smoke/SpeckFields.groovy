/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spockframework.smoke

import org.junit.runner.RunWith
import org.spockframework.dsl.*

/**
 * A ...
 
 * @author Peter Niederwieser
 */
@Speck
@RunWith(Sputnik)
class InitializationOfUnsharedFields {
  def a
  def b = 1
  int c
  int d = 1

  def setupSpeck() {
    assert a == null
    assert b == null
    assert c == 0
    assert d == 0

    // should have no effect on subsequent execution
    a = b = c = d = 99999
  }

  def setup() {
    assert a == null
    assert b == 1
    assert c == 0
    assert d == 1

    b = 2
  }

  def "feature 1"() {
    expect:
    a == null
    b == 2
    c == 0
    d == 1

    // should have no effect on subsequent execution
    a = b = c = d = 11111
  }

  def "feature 2"() {
    expect:
    a == null
    b == 2
    c == 0
    d == 1
  }
}

@Speck
@RunWith(Sputnik)
class InitializationOfSharedFields {
  @Shared def a
  @Shared def b = 1
  @Shared int c
  @Shared int d = 1

  def setupSpeck() {
    assert a == null
    assert b == 1
    assert c == 0
    assert d == 1

    b = 2
  }

  def setup() {
    assert a == null
    assert b == 2
    assert c == 0 || c == 2 // changed by setup
    assert d == 1 || d == 2 // changed by feature 1

    c = 2
  }

  def "feature 1"() {
    expect:
    a == null
    b == 2
    c == 2
    d == 1

    d = 2
  }

  def "feature 2"() {
    expect:
    a == null
    b == 2
    c == 2
    d == 2
  }
}

@Speck
@RunWith(Sputnik)
class DefaultValuesOfUnsharedFields {
  boolean f1
  char f2
  byte f3
  short f4
  int f5
  long f6
  float f7
  double f8

  Boolean F1
  Character F2
  Byte F3
  Short F4
  Integer F5
  Long F6
  Float F7
  Double F8

  def F9
  Map F10

  def "primitive types"() {
    expect:
      f1 == false
      f2 == (char)0
      f3 == (byte)0
      f4 == (short)0
      f5 == 0
      f6 == 0l
      f7 == 0f
      f8 == 0d
  }

  def "wrapper types"() {
    expect:
      F1 == null
      F2 == null
      F3 == null
      F4 == null
      F5 == null
      F6 == null
      F7 == null
      F8 == null
  }

  def "reference types"() {
    expect:
      F9 == null
      F10 == null
  }
}

@Speck
@RunWith(Sputnik)
class DefaultValuesOfSharedFields {
  @Shared boolean f1
  @Shared char f2
  @Shared byte f3
  @Shared short f4
  @Shared int f5
  @Shared long f6
  @Shared float f7
  @Shared double f8

  @Shared Boolean F1
  @Shared Character F2
  @Shared Byte F3
  @Shared Short F4
  @Shared Integer F5
  @Shared Long F6
  @Shared Float F7
  @Shared Double F8

  @Shared def F9
  @Shared Map F10

  def "primitive types"() {
    expect:
      f1 == false
      f2 == (char)0
      f3 == (byte)0
      f4 == (short)0
      f5 == 0
      f6 == 0l
      f7 == 0f
      f8 == 0d
  }

  def "wrapper types"() {
    expect:
      F1 == null
      F2 == null
      F3 == null
      F4 == null
      F5 == null
      F6 == null
      F7 == null
      F8 == null
  }

  def "reference types"() {
    expect:
      F9 == null
      F10 == null
  }
}
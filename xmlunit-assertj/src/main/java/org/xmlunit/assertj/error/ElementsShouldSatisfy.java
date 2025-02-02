/*
  This file is licensed to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.xmlunit.assertj.error;

import java.util.List;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

import static java.lang.String.format;

/**
 * @since XMLUnit 2.8.3
 */
public class ElementsShouldSatisfy extends BasicErrorMessageFactory {

    public static ErrorMessageFactory elementsShouldSatisfy(Object actual,
        List<UnsatisfiedRequirement> elementsNotSatisfyingRestrictions, AssertionInfo info) {
        return new ElementsShouldSatisfy(actual, elementsNotSatisfyingRestrictions, info);
    }

    private ElementsShouldSatisfy(Object actual,
        List<UnsatisfiedRequirement> elementsNotSatisfyingRestrictions, AssertionInfo info) {
        super(format("%n" +
                     "Expecting all elements of:%n" +
                     "  %s%n" +
                     "to satisfy given requirements, but these elements did not:%n%n"
                     + describeErrors(elementsNotSatisfyingRestrictions, info),
                     actual));
    }

    private static String describeErrors(List<UnsatisfiedRequirement> elementsNotSatisfyingRequirements, AssertionInfo info) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (UnsatisfiedRequirement ur : elementsNotSatisfyingRequirements) {
            if (!first) {
                sb.append(format("%n%n"));
            }
            first = false;
            sb.append(ur.describe(info));
        }
        return escapePercent(sb.toString());
    }

    private static String escapePercent(String s) {
        return s.replace("%", "%%");
    }

    public static class UnsatisfiedRequirement {
        private final Object elementNotSatisfyingRequirements;
        private final String errorMessage;

        public UnsatisfiedRequirement(Object elementNotSatisfyingRequirements, String errorMessage) {
            this.elementNotSatisfyingRequirements = elementNotSatisfyingRequirements;
            this.errorMessage = errorMessage;
        }

        public String describe(AssertionInfo info) {
            return format("%s%nerror: %s", info.representation().toStringOf(elementNotSatisfyingRequirements), errorMessage);
        }

        @Override
        public String toString() {
            return format("%s %s", elementNotSatisfyingRequirements, errorMessage);
        }
    }
}

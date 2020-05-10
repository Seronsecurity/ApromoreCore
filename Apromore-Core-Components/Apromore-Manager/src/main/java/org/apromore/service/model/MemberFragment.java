/*-
 * #%L
 * This file is part of "Apromore Core".
 * 
 * Copyright (C) 2012 - 2017 Queensland University of Technology.
 * %%
 * Copyright (C) 2018 - 2020 The University of Melbourne.
 * %%
 * Copyright (C) 2020, Apromore Pty Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

/**
 *
 */
package org.apromore.service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="mailto:chathura.ekanayake@gmail.com">Chathura C. Ekanayake</a>
 */
public class MemberFragment {

    private Integer fragmentId;
    private int fragmentSize;
    private double distance;

    private List<ProcessAssociation> processAssociations = new ArrayList<ProcessAssociation>();


    /**
     * Default Constructor.
     */
    public MemberFragment() {
    }


    public MemberFragment(Integer fragmentId) {
        this.fragmentId = fragmentId;
    }

    public Integer getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(Integer fragmentId) {
        this.fragmentId = fragmentId;
    }

    public int getFragmentSize() {
        return fragmentSize;
    }

    public void setFragmentSize(int fragmentSize) {
        this.fragmentSize = fragmentSize;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<ProcessAssociation> getProcessAssociations() {
        return processAssociations;
    }

    public void setProcessAssociations(List<ProcessAssociation> processAssociations) {
        this.processAssociations = processAssociations;
    }
}

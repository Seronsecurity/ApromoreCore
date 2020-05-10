/*-
 * #%L
 * This file is part of "Apromore Core".
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
package org.apromore.processmining.plugins.bpmn;

import java.util.Collection;
import java.util.Map;

import org.apromore.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.apromore.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.apromore.processmining.models.graphbased.directed.bpmn.elements.DataObject;
import org.apromore.processmining.models.graphbased.directed.bpmn.elements.Swimlane;
import org.xmlpull.v1.XmlPullParser;


public class BpmnDataObject extends BpmnIdName {

    private String dataObject;

	public BpmnDataObject(String tag) {
		super(tag);
	}

    protected void importAttributes(XmlPullParser xpp, Bpmn bpmn) {
        super.importAttributes(xpp, bpmn);
        String value = xpp.getAttributeValue(null, "dataObject");
        if (value != null) {
            dataObject = value;
        }
    }

    protected String exportAttributes() {
        String s = super.exportAttributes();
        if (dataObject != null) {
            s += exportAttribute("dataObject", dataObject);
        }
        return s;
    }

    public void unmarshall(BPMNDiagram diagram, Map<String, BPMNNode> id2node) {
        DataObject dataObject = diagram.addDataObject(name);
        dataObject.getAttributeMap().put("Original id", id);
        id2node.put(id, diagram.addDataObject(name));
    }

    public void unmarshall(BPMNDiagram diagram, Collection<String> elements, Map<String, BPMNNode> id2node, Swimlane lane) {
        if (elements.contains(id)) {
            DataObject dataObject = diagram.addDataObject(name);
            dataObject.getAttributeMap().put("Original id", id);
            id2node.put(id, dataObject);
        }
    }

    public void marshall(DataObject dataObject) {
        super.marshall(dataObject);
    }
}

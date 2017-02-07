/*
 * Copyright © 2009-2017 The Apromore Initiative.
 *
 * This file is part of "Apromore".
 *
 * "Apromore" is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * "Apromore" is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package org.apromore.dao.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import org.apromore.graph.canonical.ObjectRefTypeEnum;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * ObjectRef generated by hbm2java
 */
@Entity
@Table(name = "object_ref")
@Configurable("objectRef")
@Cache(expiry = 180000, size = 1000, coordinationType = CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS)
public class ObjectRef implements java.io.Serializable {

    private Integer id;
    private Boolean optional;
    private Boolean consumed;

    private ObjectRefTypeEnum type;

    private Node node;
    private Object object;
    private Set<ObjectRefAttribute> objectRefAttributes = new HashSet<>();

    /**
     * Public Constructor.
     */
    public ObjectRef() { }



    /**
     * returns the Id of this Object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the Id of this Object
     * @param id the new Id.
     */
    public void setId(final Integer id) {
        this.id = id;
    }


    @Column(name = "type", length = 30)
    @Enumerated(EnumType.STRING)
    public ObjectRefTypeEnum getType() {
        return this.type;
    }

    public void setType(ObjectRefTypeEnum type) {
        this.type = type;
    }


    @Column(name = "optional")
    public Boolean getOptional() {
        return this.optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }


    @Column(name = "consumed")
    public Boolean getConsumed() {
        return this.consumed;
    }

    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }


    @ManyToOne
    @JoinColumn(name = "nodeId")
    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @ManyToOne
    @JoinColumn(name = "objectId")
    public Object getObject() {
        return this.object;
    }

    public void setObject(Object objectType) {
        this.object = objectType;
    }


    @OneToMany(mappedBy = "objectRef", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ObjectRefAttribute> getObjectRefAttributes() {
        return this.objectRefAttributes;
    }

    public void setObjectRefAttributes(Set<ObjectRefAttribute> objectRefTypeAttributes) {
        this.objectRefAttributes = objectRefTypeAttributes;
    }

}



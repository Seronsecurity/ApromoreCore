package org.apromore.dao.model;

import org.eclipse.persistence.annotations.*;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * FragmentVersion generated by hbm2java
 */
@Entity
@Table(name = "fragment_version",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"uri"})
        }
)
@Configurable("fragmentVersion")
@Cacheable(true)
@Cache(type = CacheType.SOFT_WEAK, isolation = CacheIsolationType.SHARED, expiry = 60000, size = 1000, alwaysRefresh = true, disableHits = true, coordinationType = CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS)
public class FragmentVersion implements Serializable {

    private Integer id;
    private String uri;
    private String childMappingCode;
    private Integer lockStatus;
    private Integer lockCount;
    private String derivedFromFragment;
    private Integer fragmentSize;
    private String fragmentType;
    private String newestNeighbor;

    private Content content;
    private Cluster cluster;
    private Fragment fragment;

    private Set<ProcessModelVersion> processModelVersions = new HashSet<ProcessModelVersion>(0);
    private Set<ProcessModelVersion> rootProcessModelVersions = new HashSet<ProcessModelVersion>(0);
    private Set<FragmentVersionDag> childFragmentVersionDags = new HashSet<FragmentVersionDag>(0);



    /**
     * Public Constructor.
     */
    public FragmentVersion() { }



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



    /**
     * The URI of this fragmentVersion.
     * @return the uri
     */
    @Column(name = "uri", length = 40)
    public String getUri() {
        return this.uri;
    }

    /**
     * The URI of this fragmentVersion.
     * @param newUri the new uri.
     */
    public void setUri(final String newUri) {
        this.uri = newUri;
    }


    @Column(name = "child_mapping_code")
    public String getChildMappingCode() {
        return this.childMappingCode;
    }

    public void setChildMappingCode(final String newChildMappingCode) {
        this.childMappingCode = newChildMappingCode;
    }


    @Column(name = "lock_status")
    public Integer getLockStatus() {
        return this.lockStatus;
    }

    public void setLockStatus(final Integer newLockStatus) {
        this.lockStatus = newLockStatus;
    }


    @Column(name = "lock_count")
    public Integer getLockCount() {
        return this.lockCount;
    }

    public void setLockCount(final Integer newLockCount) {
        this.lockCount = newLockCount;
    }


    @Column(name = "derived_from_fragment")
    public String getDerivedFromFragment() {
        return this.derivedFromFragment;
    }

    public void setDerivedFromFragment(final String newDerivedFromFragment) {
        this.derivedFromFragment = newDerivedFromFragment;
    }


    @Column(name = "fragment_size")
    public Integer getFragmentSize() {
        return this.fragmentSize;
    }

    public void setFragmentSize(final Integer newFragmentSize) {
        this.fragmentSize = newFragmentSize;
    }


    @Column(name = "fragment_type")
    public String getFragmentType() {
        return this.fragmentType;
    }

    public void setFragmentType(final String newFragmentType) {
        this.fragmentType = newFragmentType;
    }


    @Column(name = "newest_neighbor")
    public String getNewestNeighbor() {
        return this.newestNeighbor;
    }

    public void setNewestNeighbor(final String newNewestNeighbor) {
        this.newestNeighbor = newNewestNeighbor;
    }



    @ManyToMany(mappedBy = "fragmentVersions")
    @JoinTable(name = "process_fragment_map",
            joinColumns = { @JoinColumn(name =" fragmentVersionId", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "processModelVersionId", nullable=  false) }
    )
    public Set<ProcessModelVersion> getProcessModelVersions() {
        return this.processModelVersions;
    }

    public void setProcessModelVersions(Set<ProcessModelVersion> processModelVersions) {
        this.processModelVersions = processModelVersions;
    }



    @ManyToOne
    @JoinColumn(name = "contentId")
    public Content getContent() {
        return this.content;
    }

    public void setContent(final Content newContent) {
        this.content = newContent;
    }

    @ManyToOne
    @JoinColumn(name = "clusterId")
    public Cluster getCluster() {
        return this.cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    @ManyToOne
    @JoinColumn(name = "fragmentId")
    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(final Fragment newFragment) {
        this.fragment = newFragment;
    }



    @OneToMany(mappedBy = "rootFragmentVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ProcessModelVersion> getRootProcessModelVersions() {
        return this.rootProcessModelVersions;
    }

    public void setRootProcessModelVersions(Set<ProcessModelVersion> processModelVersions) {
        this.rootProcessModelVersions = processModelVersions;
    }

    @OneToMany(mappedBy = "childFragmentVersion", cascade = CascadeType.ALL, targetEntity = FragmentVersionDag.class)
    public Set<FragmentVersionDag> getChildFragmentVersionDags() {
        return this.childFragmentVersionDags;
    }

    public void setChildFragmentVersionDags(Set<FragmentVersionDag> childFragmentVersionDags) {
        this.childFragmentVersionDags = childFragmentVersionDags;
    }

}



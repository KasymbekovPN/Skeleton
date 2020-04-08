package org.KasymbekovPN.Skeleton.protocol;

import java.util.Objects;

public class SimpleProtocolEntity implements ProtocolEntity {

    private String protocol;

    public SimpleProtocolEntity(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean verify(ProtocolEntity protocolEntity) {
        return equals(protocolEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleProtocolEntity that = (SimpleProtocolEntity) o;
        return Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol);
    }
}

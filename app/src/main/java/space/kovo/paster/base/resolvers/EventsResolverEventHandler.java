package space.kovo.paster.base.resolvers;

public interface EventsResolverEventHandler<DATA> {
    void apply(DATA data);
}

package space.kovo.paster.activities.itemsActivity.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.kovo.paster.R;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final Context context;
    private final List<ItemResponseDTO> data;

    public ItemsAdapter(Context context, List<ItemResponseDTO> data) {
        this.context = context;
        this.data = data;
    }

    public class Observer extends RecyclerView.AdapterDataObserver {

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            // do nothing
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemsAdapterListeners listeners;
        private final TextView itemPreview;
        private final LinearLayout itemPreviewWrapper;
        private final TextView itemIdentificator;

        public ViewHolder(View view) {
            super(view);
            listeners = new ItemsAdapterListeners(context);
            itemPreview = view.findViewById(R.id.itemPreview);
            itemIdentificator = view.findViewById(R.id.itemIdentificator);
            itemPreviewWrapper = view.findViewById(R.id.itemPreviewWrapper);
            view.setOnClickListener(listeners.copyToClipboardListener());
        }


        //@Override


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ItemResponseDTO dto = data.get(position);
        viewHolder.itemPreview.setText(dto.getPreview());
        viewHolder.itemIdentificator.setText(String.valueOf(dto.getId()));
    }

//    @Override
//    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
//        .registerObserver(observer);
//    }

//    @Override
//    public void OnItemsAdded(@NonNull RecyclerView recyclerView, int positionStart, int itemCount) {
//
//    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
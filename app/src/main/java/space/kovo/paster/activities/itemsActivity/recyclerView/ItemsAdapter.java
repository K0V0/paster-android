package space.kovo.paster.activities.itemsActivity.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
        private final Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            listeners = new ItemsAdapterListeners(context);
            itemPreview = view.findViewById(R.id.itemPreview);
            itemIdentificator = view.findViewById(R.id.itemIdentificator);
            itemPreviewWrapper = view.findViewById(R.id.itemPreviewWrapper);
            deleteButton = view.findViewById(R.id.itemDelete);
            inits(view);
        }

        private void inits(View view) {
            view.setOnClickListener(listeners.copyToClipboardListener());
            deleteButton.setOnClickListener(listeners.deleteItemListener(view));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ItemResponseDTO dto = data.get(position);
        viewHolder.itemPreview.setText(dto.getPreview());
        viewHolder.itemIdentificator.setText(String.valueOf(dto.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
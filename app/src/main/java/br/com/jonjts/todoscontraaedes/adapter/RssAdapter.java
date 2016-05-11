package br.com.jonjts.todoscontraaedes.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.Button;

import java.net.URI;
import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import br.com.jonjts.todoscontraaedes.R;

/**
 * Created by jon_j on 21/04/2016.
 */
public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> {

    List<RSSItem> mItems;
    private Context context;

    public RssAdapter(List<RSSItem> mItems, Context context) {
        this.mItems = mItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final RSSItem item = mItems.get(i);
        viewHolder.txtTitulo.setText(Html.fromHtml(item.getTitle()));
        //viewHolder.imgRss.setImageResource(item.get);
        viewHolder.txtData.setText(item.getDate());
        viewHolder.txtDescricao.setText(fixDescricao(item.getDescription()));
        viewHolder.imgRss.setVisibility(View.GONE);

        viewHolder.btnDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getLink() != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink().toString()));
                    context.startActivity(browserIntent);
                }
            }
        });


        viewHolder.btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getLink() != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, item.getLink().toString());
                    sendIntent.setType("text/plain");
                    context.startActivity((Intent.createChooser(sendIntent, "Compartihar Notícia")));
                }
            }
        });

        setFadeAnimation(viewHolder.fade);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    private Spanned fixDescricao(String descricao) {
        if (descricao.length() > 600) {
            descricao = descricao.substring(0, 600);
            descricao += "...";
        }
        return Html.fromHtml(descricao);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<RSSItem> getItems() {
        return mItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgRss;
        public TextView txtTitulo;
        public TextView txtDescricao;
        public TextView txtData;
        public Button btnDescricao;
        public Button btnCompartilhar;
        public View fade;

        public ViewHolder(View itemView) {
            super(itemView);
            imgRss = (ImageView) itemView.findViewById(R.id.imgRss);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDescricao = (TextView) itemView.findViewById(R.id.txtDescricao);
            txtData = (TextView) itemView.findViewById(R.id.txtData);
            btnDescricao = (Button) itemView.findViewById(R.id.btnDescricao);
            btnCompartilhar = (Button) itemView.findViewById(R.id.btnCompartilhar);
            fade = itemView.findViewById(R.id.fade);
        }
    }
}



package com.example.ctrlbox_app;

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

    class TableViewAdapter(private val boxlist: List<Datamodels>):  RecyclerView.Adapter<TableViewAdapter.RowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_list_item, parent, false)
        return RowViewHolder(itemView)
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.adapterPosition

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            holder.apply {
                setHeaderBg(txtBoxid)
                setHeaderBg(txtGetFrom)
                setHeaderBg(txtSendto)
                setHeaderBg(txtTransDate)
                setHeaderBg(txtTransType)

                txtBoxid.text = "Boxid"
                txtGetFrom.text = "GetFrom"
                txtSendto.text = "Sendto"
                txtTransDate.text = "TransDate"
                txtTransType.text = "TransType"
            }
        } else {
            val modal = boxlist[rowPos - 1]

            holder.apply {
                setContentBg(txtBoxid)
                setContentBg(txtGetFrom)
                setContentBg(txtSendto)
                setContentBg(txtTransDate)
                setContentBg(txtTransType)


                txtBoxid.text = modal.boxId.toString()
                txtGetFrom.text = modal.getFrom.toString()
                txtSendto.text = modal.sendTo.toString()
                txtTransDate.text = modal.transDate.toString()
                txtTransType.text = modal.transType.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return boxlist.size + 1 // one more to add header row
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBoxid: TextView = itemView.findViewById(R.id.boxid)
        val txtGetFrom: TextView = itemView.findViewById(R.id.getFrom)
        val txtSendto: TextView = itemView.findViewById(R.id.sendto)
        val txtTransDate: TextView = itemView.findViewById(R.id.transDate)
        val txtTransType: TextView = itemView.findViewById(R.id.transType)
    }
}
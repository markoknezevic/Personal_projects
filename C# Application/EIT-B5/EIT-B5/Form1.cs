using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EIT_B5
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void unosToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            Tip_Antikviteta t = new Tip_Antikviteta();
            t.Show();
        }

        private void izlazAltIToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void periodiToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Periodi p = new Periodi();
            p.Show();
        }

        private void poArhiologuToolStripMenuItem_Click(object sender, EventArgs e)
        {
            tIP t = new tIP();
            t.Show();
        }

        private void poTipuAkvititetaToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Po_Antikvitetu po_ank = new Po_Antikvitetu();
            po_ank.Show();
        }
    }
}
